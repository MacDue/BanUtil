#!/bin/sh
until java -jar ./build/libs/BanUtil-0.1.jar; do
    echo "BanUtil crashed with exit code $?.  Respawning.." >&2
    sleep 1
done

