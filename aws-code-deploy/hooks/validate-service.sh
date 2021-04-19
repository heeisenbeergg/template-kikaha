#!/bin/bash
# Validates and check if the server is actually running.
# It will use 'curl' to probe the health-check for healthy feedback
# for at most 10 times (it's configurable value).

if [ -f /opt/env-vars.sh ]; then
    . /opt/env-vars.sh
fi

# VARIABLES
MAX_ATTEMPTS=120
FALLBACK_TIME=1
HEALTH_CHECK_URL="http://127.0.0.1:8080/api/internal/health-check"

C=0
while [ ! "$C" = "${MAX_ATTEMPTS}" ]; do
    echo "Checking for health instance in ${HEALTH_CHECK_URL}"

    curl -sS -f ${HEALTH_CHECK_URL} && \
        echo "Service is healthy" && \
        exit 0

    sleep ${FALLBACK_TIME}
    C=$(expr ${C} + 1)
done

echo "Service is unhealthy"
exit 1