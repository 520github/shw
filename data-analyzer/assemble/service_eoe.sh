#!/bin/sh


APP_HOME=$(dirname $(cd "$(dirname "$0")"; pwd))
#echo $APP_HOME
#echo $1
#echo $2
APP_NAME=data-analyzer
. /etc/init.d/functions
. /etc/profile

_jar=`ls ${APP_HOME}/lib/*.jar`
_classpath=`echo ${_jar} | sed -e 's/ /:/g'`

JAVA_HOME=/usr/local/jdk6/
CLASSPATH=.:$APP_HOME/com:$APP_HOME/conf:${_classpath}
LOG=/var/log/android/${APP_NAME}/eoe
PID=/var/run/android/${APP_NAME}_eoe
MAIN_CLASS="com.babeeta.appstore.android.DataAnalyzerService"
JAVA_OPTS="-Djava.net.preferIPv4Stack=true -Xms512m -Xmx1024m  "
APP_OPTS="-r /shw-nfs/jobs/www.eoemarket.com/latest/"
RUNAS="root"

mkdir -p $LOG


if test -z $1 ; then
	echo "Usage service.sh [start|stop]"
	exit 1
else
	case $1 in

		'start' )
			$APP_HOME/sbin/jsvc -home $JAVA_HOME  -procname $APP_NAME -pidfile $PID -user $RUNAS -outfile $LOG/std.log -errfile $LOG/err.log -cp $CLASSPATH $JAVA_OPTS $MAIN_CLASS $APP_OPTS
			success
			echo "Starting dataAnalyzer target $APP_OPTS....."
			exit 0;
		;;

		'stop' )
			if test -s $PID ; then
                               #echo $PID
                               #echo `cat $PID`
				kill `cat $PID`
				success
				echo 'Stopping analyze......'
				exit 0;
			else
				failure
				echo "DataAnalyzer target $APP_OPTS is not running. No need to stop it"
				exit 1;
			fi
		;;
	esac
	exit 0
fi

