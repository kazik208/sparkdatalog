#/bin/bash

set -ue

function start_hdfs {
  HOSTNAME=`hostname`
  HADOOP_HOME="$2"
  CONF_DIR="$HADOOP_HOME/etc/hadoop"
  sed -e "s/\${HOSTNAME}/$HOSTNAME/" $CONF_DIR/core-site-template.xml > $CONF_DIR/core-site.xml
  rm -r /tmp/data
  mkdir -p /tmp/data
  mkdir -p /tmp/tmp
  $HADOOP_HOME/bin/hadoop namenode -format
  $HADOOP_HOME/sbin/hadoop-daemon.sh start namenode
  $HADOOP_HOME/sbin/hadoop-daemon.sh start datanode
}

function stop_hdfs {
  HADOOP_HOME="$2"
  $HADOOP_HOME/sbin/hadoop-daemon.sh stop namenode
  $HADOOP_HOME/sbin/hadoop-daemon.sh stop datanode
}

function copy {
  HADOOP_HOME="$2"
  if [ "$#" -eq 3 ]; then
    FILES_FOLDER="$3"
    $HADOOP_HOME/bin/hadoop fs -copyFromLocal $FILES_FOLDER/*.txt /
  fi
}

function print_usage {
      echo "Usage: $0 COMMNAD HADOOP_HOME [FILES_FOLDER]"
      echo "Where: COMMAND = start | stop | copy"
}

function check_args {
  if [ "$#" -lt 2 ]; then
      print_usage "$@"
      exit 1
  fi
  
  if [[ "$#" -ne 3 && "$1" == "copy" ]]; then
      echo "Please provide folder, which contents will be copied to hdfs"
      exit 1
  fi
}

check_args "$@"

COMMAND="$1"

if [ "$COMMAND" == "start" ]; then
  start_hdfs "$@"
  copy "$@"
elif [ "$COMMAND" == "stop" ]; then
  stop_hdfs "$@"
elif [ "$COMMAND" == "copy" ]; then
  copy "$@"
else
  print_usage
fi
