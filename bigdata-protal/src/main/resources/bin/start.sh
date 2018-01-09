#!/usr/bin/env bash
moduleName="bdstat"
pidPath="/home/bdstat/run/$moduleName-tpid"
rm -f $pidPath
java -jar bdstat.jar > run.log 2>&1 &
echo "started"