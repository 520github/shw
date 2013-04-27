#!/bin/sh


APP_HOME=$(dirname $(cd "$(dirname "$0")"; pwd))
echo $APP_HOME
echo $1
echo $2
APP_NAME=data-analyzer
. /etc/init.d/functions
. /etc/profile

_jar=`ls ${APP_HOME}/lib/*.jar`
_classpath=`echo ${_jar} | sed -e 's/ /:/g'`

CLASSPATH=.:$APP_HOME/com:$APP_HOME/conf:${_classpath}
LOG=/var/log/android/${APP_NAME}
PID=/var/run/android/${APP_NAME}
MAIN_CLASS="com.babeeta.appstore.DataAnalyzerService"
JAVA_OPTS="-Djava.net.preferIPv4Stack=true -Xms1024m -Xmx1024m"
APP_OPTS="-r $2"
RUNAS="root"

mkdir -p $LOG


if test -z $1 ; then
	echo "Usage service.sh [start|stop]"
	exit 1
else
	case $1 in

		'start' )
			$APP_HOME/sbin/jsvc -procname $APP_NAME -pidfile $PID -user $RUNAS -outfile $LOG/std.log -errfile $LOG/err.log -cp $CLASSPATH $JAVA_OPTS $MAIN_CLASS $APP_OPTS
			success
			echo "Starting dataAnalyzer target $2....."
			exit 0;
		;;

		'stop' )
			if test -s $PID ; then
				kill `cat $PID`
				success
				echo 'Stopping analyze......'
				exit 0;
			else
				failure
				echo "DataAnalyzer target $2 is not running. No need to stop it"
				exit 1;
			fi
		;;
	esac
	exit 0
fi

