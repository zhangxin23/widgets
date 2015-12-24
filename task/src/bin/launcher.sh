#!/bin/bash

PRG="$0"

while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`

[ -z "$TASK_HOME" ] && TASK_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

echo "TASK HOME:$TASK_HOME"

source /etc/profile
export JAVA_HOME=$JAVA_HOME

if [ "$JAVA_HOME" = "" ]; then
    source ~/.bashrc
    export JAVA_HOME=$JAVA_HOME
    if [ "$JAVA_HOME" = "" ]; then
        echo "Error: JAVA_HOME is not set."
        exit 1
    fi
fi
echo "set JAVA_HOME=$JAVA_HOME"
JAVA="$JAVA_HOME/bin/java"

timezone="Asia/Shanghai"

MAIN=$1
if [ -z "$MAIN" ]; then
    echo "exec main null"
    exit 1
fi

shift

CONF_DIR="$TASK_HOME/config"
CLASSPATH=$(echo $(ls "$TASK_HOME"/lib/*.jar) | sed "s/ /:/g")
CLASSPATH="$CONF_DIR:$CLASSPATH"

JAVA_OPTS="-Xms3072m -Xmx3072m -Duser.timezone=$timezone -DappKey=$MAIN"

exec "$JAVA" $JAVA_OPTS -cp "$CLASSPATH"  "$MAIN" "$@"
