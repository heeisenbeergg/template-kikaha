#!/bin/sh
#cd /opt/application

if [ -f /opt/env-vars.sh ]; then
    . /opt/env-vars.sh
fi

# VARIABLES
OS="`uname`"

case $OS in
 'Darwin') TOTAL_MEMORY="1024" ;;
 *) TOTAL_MEMORY=$(free -m| grep Mem| sed 's/  */ /g'| cut -d ' ' -f 2) ;;
esac

HEAP_MEM=${HEAP_MEM:-$(eval expr "$TOTAL_MEMORY \\* 60 / 100")}
JAVA_OPTS=${JAVA_OPTS:-}

# MAIN
exec java -Xmx${HEAP_MEM}m ${JAVA_OPTS} -cp .:lib/* kikaha.core.cdi.ApplicationRunner
