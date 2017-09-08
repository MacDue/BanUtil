#!/bin/sh
until java -jar BanUtil.jar; do
    echo "BanUtil crashed with exit code $?.  Respawning.." >&2
    sleep 1
done

