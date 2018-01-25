package xyz.sidetrip.banutil.web;

import de.lucavinci.http.*;
import xyz.sidetrip.banutil.BanUtil;
import xyz.sidetrip.banutil.UtilDue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;

public class BanUtilStatusPage {

    private final HttpServer http;
    private final BanUtil.Status botStatus;

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
        this.http.registerHandler("/", new BanUtilStatus());
        this.http.start();
    }

    private class BanUtilStatus implements HttpHandler.Request {

        private static final String PRE_CSS = "color: #FF6961;" +
                "border-style: solid;" +
                "text-shadow: 1px 2px 10px #2D2F3C;" +
                "border: #2D2F3C;" +
                "font-weight: bolder;" +
                "background-color: 2D2F3C;" +
                "padding-bottom: 10px;" +
                "display: inline-block;" +
                "padding-right:  5px;" +
                "margin: 0px;";

        private static final String PRE_TAG = "<pre style=\"" + PRE_CSS + "\">";

        private static final String INVITE_FORMAT = "https://discordapp.com/oauth2/authorize?client_id=%s&scope=bot&permissions=%d";


        private String createInviteLinkHTML() {
            String appId = BanUtil.getClient().getApplicationClientID();
            String link = String.format(INVITE_FORMAT, appId, BanUtil.REQUIRED_PERMISSIONS);
            return "<h3 style=\"display:inline\"><a href=\""
                    + link + "\">Invite to server</a></h3> (remember BanUtil is a single server bot)";
        }

        private String getExceptionTraceHTML() {
            if (botStatus.lastError == null) {
                return "";
            }
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            botStatus.lastError.printStackTrace(printWriter);
            printWriter.flush();
            return "<br><br><b>This may (or may not) be related:</b><br>" + PRE_TAG + writer.toString() + "</pre>";
        }

        @Override
        public HttpResponse onRequest(HttpRequest httpRequest, InetAddress inetAddress) {
            StringBuilder statusPage = new StringBuilder("<body style=\"background-color: #95D3BD;\">");
            if (botStatus.allGood) {
                statusPage.append(PRE_TAG + BanUtil.WELCOME + "</pre>");
                statusPage.append("<p>Oh I think you'll find this ban hammer is fully operational.</p>");
                statusPage.append("<h1 style=\"color:#FF6961\">Bans since last restart: <span style=\"font-size:10px\">10000000000000000000000000000000</span>"
                        + botStatus.bansSinceLastRestart + "</h1>");
                statusPage.append(this.createInviteLinkHTML());

            } else {
                if (botStatus.dead) {
                    statusPage.append(PRE_TAG + UtilDue.BIG_FLASHY_ERROR + "</pre>");
                    statusPage.append("<h2>The bot is dead!</h2>");
                    statusPage.append("<p>Check that your token is correct and BanUtil is the latest version (or wait for the bot to login)!</a>");
                } else {
                    statusPage.append("<h4>Something is not quite right...</h4>");
                    statusPage.append("<b>Config errors:</b><br>");
                    statusPage.append(PRE_TAG + BanUtil.CONFIG.getValidationErrors() + "</pre><br>");
                    statusPage.append("<b>If you've just deployed (correctly) the bot inviting it should solve these errors!</b><br>");
                    statusPage.append(this.createInviteLinkHTML());
                }
            }
            statusPage.append(this.getExceptionTraceHTML());
            statusPage.append("<p>Refresh page for updates.</p>");
            statusPage.append("<p>Get your own BanUtil: <a href=\"" + BanUtil.REPO + "\"" + ">" + BanUtil.REPO + "</a>");
            statusPage.append("</body>");
            return new HttpResponse(statusPage.toString(), HttpStatusCode.OK, HttpMimeType.TXT) {
                @Override
                public String format() {
                    // Should fix make the response standard compliant.
                    return super.format().replace("\n", "\r\n");
                }
            };
        }
    }
}
