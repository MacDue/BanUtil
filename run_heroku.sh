#!/bin/sh

export HEROKU=true
export ENABLE_WEB=true
export WEB_PORT=80

until java -jar ./build/libs/BanUtil-0.1.jar; do
    echo "BanUtil crashed with exit code $?.  Respawning.." >&2
    sleep 1
done

