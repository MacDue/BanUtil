package xyz.sidetrip.banutil.web;

import de.lucavinci.http.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import unij.UniJ;
import xyz.sidetrip.banutil.BanUtil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class BanUtilStatusPage {

    private final HttpServer http;
    private final BanUtil.Status botStatus;
    private final Map<String, byte[]> webAssets = new HashMap<>();
    private final String root;

    /*
    A simple status page for BanUtil.
    Make it's easy to alter users to errors with a Heroku deploy and give them a good invite link.

    Please excuse the messy inline HTML.
     */
    public BanUtilStatusPage(BanUtil.Status status) {
        BanUtil.LOGGER.info("Creating status page...");
        this.botStatus = status;
        this.http = new HttpServer(
                Integer.parseInt(System.getenv().getOrDefault("PORT", "8080")));
        //this.http.registerHandler("/", new BanUtilStatus());
        this.http.start();
        this.root = "web/";
        this.loadAssets(this.root);
        this.loadAssets("js/");

        UniJ.setPort(8081);
        UniJ.start();
    }

    @EventSubscriber
    public void messageWebHook(MessageReceivedEvent event) {
        if (UniJ.getNumberOfConnectedClients() > 0) {
            UniJ.executeAll("test", event.getMessage().getContent());
        }
    }

    private void loadAssets(String dir) {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        try {
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                if (entry.getName().startsWith(dir)) { //filter according to the path
                    if (!entry.isDirectory()) {
                        String relPath = entry.getName().replace(this.root, "/");
                        try {
                            webAssets.put(relPath, IOUtils.toByteArray(jar.getInputStream(entry)));
                            this.http.registerHandler(relPath, new StaticContent(relPath));
                            BanUtil.LOGGER.info("Loaded " + relPath);
                        } catch (IOException e) {
                            BanUtil.LOGGER.error("Unable to load %s - %s", relPath, e);
                        }
                    }
                }
            }
            jar.close();
        } catch (IOException e) {
            BanUtil.LOGGER.error("Unable to load web resources", e);
        }
    }


    private class StaticContent extends BanUtilRequest {

        String resource;

        public StaticContent(String resource) {
            this.resource = resource;
        }

        public byte[] getBody() {
            return webAssets.get(this.resource);
        }

        public HttpMimeType mimeType() {
            return HttpMimeType.find(FilenameUtils.getExtension(this.resource));
        }
    }


    private abstract class BanUtilRequest implements HttpHandler.Request {

        private String createInviteLinkHTML() {
            String appId = BanUtil.getClient().getApplicationClientID();
            return "";
            //String link = String.format(INVITE_FORMAT, appId, BanUtil.REQUIRED_PERMISSIONS);
           // return "<h3 style=\"display:inline\"><a href=\""
           //         + link + "\">Invite to server</a></h3> (remember BanUtil is a single server bot)";
        }

        private String getExceptionTraceHTML() {
            if (botStatus.lastError == null) {
                return "";
            }
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            botStatus.lastError.printStackTrace(printWriter);
            printWriter.flush();
            return "";
            //return "<br><br><b>This may (or may not) be related:</b><br>" + PRE_TAG + writer.toString() + "</pre>";
        }

        protected abstract byte[] getBody();

        protected abstract HttpMimeType mimeType();

        @Override
        public HttpResponse onRequest(HttpRequest httpRequest, InetAddress inetAddress) {
            byte[] body = getBody();
            byte[] padding = new byte[body.length/2];
            Arrays.fill(padding, (byte)' ');
            return new HttpResponse(ArrayUtils.addAll(body, padding), mimeType()) {
                @Override
                public String format() {
                    // Should make the HTTP response standard compliant.
                    return super.format().replace("\n", "\r\n");
                }
            };
        }
    }
}
