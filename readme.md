# BanUtil

[![Build Status](https://travis-ci.org/MacDue/BanUtil.svg?branch=master)](https://travis-ci.org/MacDue/BanUtil)

###### BanUtil is simple a self hosted Discord bot for helping with moderation.

### About

#### Features
 - Mod log with reasons & images.
 - Role based permissions.
 - Web status page.

#### How the bot works

On your server you create ``Moderator``, ``Can kick``, ``Can ban`` roles for your mods and ``Mute``, and ``Warned`` punishment roles.

None of the roles need to have any special permissions (though you'd probably want the mute role to mute people).

Giving someone the ``Moderator`` role will allow them to mute and warn users, and adding the "Can kick/ban" roles will do the obvious.

All actions will be logged in a channel you choose with reasons (that can contain images).

___

### Setup (Heroku)

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/MacDue/BanUtil/tree/master)

1. [Create a new bot account.](https://discordapp.com/developers/applications/me)
2. [Sign up for a free Heroku account.](https://signup.heroku.com)
3. Click the button above.
4. Fill in the token and role IDs. 
5. Click ![deploy](https://i.imgur.com/IJX7Hob.png?1)
6. Heroku will build and deploy the bot in about 30 seconds.
7. Click ![view](https://i.imgur.com/Bq1bwoR.png)

If you configured the bot correctly clicking view should take you to a page like:
![status ok](https://i.imgur.com/jVvPCZA.png)

Note: before the bot is invited to your server the page will say "ERROR".
If it does not go away after the bot joins your server (and you refresh the page) something is wrong.

##### Heroku free "sleeping apps"

Your bot will go to sleep if the status page is not checked in ~30 mins. To wake up your bot just open the page (whatyoucalledtheapp.herokuapp.com).

___


### Setup (self hosted)

**Requirements:** Java 8 JDK & git

Building: 
```sh
git clone https://github.com/MacDue/BanUtil
cd BanUtil
./gradlew stage
```
Config: edit the exports at the start of ``run_local.sh``

You should end up with something like this:

```sh
export TOKEN="MzM4Dz0PzIfUofQ.Hw2MKNcMDzs__.0SNsT7dyEn1zrg058NQAUgYxOb5XZ"
export MOD_ROLE_ID=355416210822922241
export CAN_BAN_ROLE_ID=355414578974162945
export CAN_KICK_ROLE_ID=355414612948025345
export WARNING_ROLE_ID=355414676819017729
export MUTE_ROLE_ID=355414636700499970
export LOG_CHANNEL_ID=323923806223597569
export SERVER_ID=148403597464436736
export OWNER_ID=132315148487622656
export RESTART_COMMAND="sh run_local.sh"
```
Starting bot: ``sh run_local.sh``

##### Enable status page

If you want the status page from the Heroku deploy add the following to the start of your run script:
```sh
export ENABLE_WEB=true
export PORT=80
```
Restart the bot and you should see the status page on ``localhost`` / your ip.

___

### P.s.

A simple way to get role IDs is to make the role mentionable then type ``\@Role``. It should show something like ``<@&355471059698319363>`` when after you send the message. The ID is the nunber between `<@&` and `>`. To find other IDs enable developer mode (under settings -> appearance), then copy IDs with the context menu.
