# opyright 1999-2022 Kaikeba NB Group Holding Ltd.
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

if [ -z "$JAVA_HOME" ]; then
    error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
fi
#===========================================================================================
# stop
#===========================================================================================
pid=`ps ax | grep -i 'jingnan' | grep java | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No jingnanServer running."
        exit -1;
fi

echo "The server(${pid}) is running..."

kill -9 ${pid}

echo "Send shutdown request to server(${pid}) OK"
#===========================================================================================
# init
#===========================================================================================

export SERVER_NAME="jingnan_web"
export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
# 获取当前目录
export BASE_DIR=`cd $(dirname $0)/.; pwd`
# 默认加载路径
export DEFAULT_SEARCH_LOCATIONS="classpath:/,classpath:/config/,file:./,file:./config/"
# 自定义默认加载配置文件路径
export CUSTOM_SEARCH_LOCATIONS=${DEFAULT_SEARCH_LOCATIONS},file:${BASE_DIR}/conf/


#===========================================================================================
# JVM Configuration
#===========================================================================================

#JAVA_OPT="${JAVA_OPT} -Xms836m -Xmx836m -Xmn314m -XX:MetaspaceSize=128m"
#JAVA_OPT="${JAVA_OPT} -XX:+UseParallelGC -XX:+UseParallelOldGC"

JAVA_OPT="${JAVA_OPT} -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:./logs/gc-default.log"

# 配置远程监控JVM
JAVA_OPT="${JAVA_OPT} -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.rmi.server.hostname=172.26.233.198 -Dcom.sun.management.jmxremote.rmi.port=9999"



JAVA_OPT="${JAVA_OPT} -jar ${BASE_DIR}/${SERVER_NAME}*.jar"
JAVA_OPT="${JAVA_OPT} ${JAVA_OPT_EXT}"
JAVA_OPT="${JAVA_OPT} --spring.config.location=${CUSTOM_SEARCH_LOCATIONS}"
# 创建日志文件目录
if [ ! -d "${BASE_DIR}/logs" ]; then
  mkdir ${BASE_DIR}/logs
fi

# 输出变量
echo "$JAVA ${JAVA_OPT}"
# 检查start.out日志输出文件
if [ ! -f "${BASE_DIR}/logs/${SERVER_NAME}.out" ]; then
  touch "${BASE_DIR}/logs/${SERVER_NAME}.out"
fi
#===========================================================================================
# 启动服务
#===========================================================================================
# 启动服务
echo "$JAVA ${JAVA_OPT}" > ${BASE_DIR}/logs/${SERVER_NAME}.out 2>&1 &
nohup $JAVA ${JAVA_OPT} jingnan_web.jingnan_web >> ${BASE_DIR}/logs/${SERVER_NAME}.out 2>&1 &
pid=`ps ax | grep -i 'jingnan' | grep java | grep -v grep | awk '{print $1}'`
echo "server(${pid}) is restarted，you can check the ${BASE_DIR}/logs/${SERVER_NAME}.out"
