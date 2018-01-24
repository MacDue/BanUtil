#!/bin/sh

export TOKEN="<token>"
export MOD_ROLE_ID="<id>"
export CAN_BAN_ROLE_ID="<id>"
export CAN_KICK_ROLE_ID="<id>"
export WARNING_ROLE_ID="<id>"
export MUTE_ROLE_ID="<id>"
export LOG_CHANNEL_ID="<id>"
export SERVER_ID="<id>"
export OWNER_ID="<id>"
export RESTART_COMMAND="sh run_local.sh"

until java -jar ./build/libs/BanUtil-0.1.jar; do
    echo "BanUtil crashed with exit code $?.  Respawning.." >&2
    sleep 1
done

