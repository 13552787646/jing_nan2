# 移动
cp -n /${jenkins_workspace_home}/${project_home}/target/${artifactId}-${VERSION}.jar /usr/local/src/${artifactId}/${VERSION}
cp -n /${jenkins_workspace_home}/${project_home}/target/classes/startup.sh /usr/local/src/${artifactId}/${VERSION}
cp -n /${jenkins_workspace_home}/${project_home}/target/classes/stop.sh /usr/local/src/${artifactId}/${VERSION}
#  清空发布目标服务器文件夹
sshpass -p ${h_pwd} ssh -p ${h_port} ${h_user}@${h_ip} 'rm -f /usr/local/src/${artifactId}/latest/*.*'
# 发部
sshpass -p ${h_pwd} scp -P ${h_port} /usr/local/src/${artifactId}/${VERSION}/${artifactId}-${VERSION}.jar ${h_user}@${h_ip}:/usr/local/src/${artifactId}/latest
sshpass -p ${h_pwd} scp -P ${h_port} /usr/local/src/${artifactId}/${VERSION}/startup.sh ${h_user}@${h_ip}:/usr/local/src/${artifactId}/latest
sshpass -p ${h_pwd} scp -P ${h_port} /usr/local/src/${artifactId}/${VERSION}/stop.sh ${h_user}@${h_ip}:/usr/local/src/${artifactId}/latest
# 停止
sshpass -p ${h_pwd} ssh -p ${h_port} ${h_user}@${h_ip} 'chmod 755 /usr/local/src/${artifactId}/latest/stop.sh'
sshpass -p ${h_pwd} ssh -p ${h_port} ${h_user}@${h_ip} '/usr/local/src/${artifactId}/latest/stop.sh'
# 启动
sshpass -p ${h_pwd} ssh -p ${h_port} ${h_user}@${h_ip} 'chmod 755 /usr/local/src/${artifactId}/latest/startup.sh'
sshpass -p ${h_pwd} ssh -p ${h_port} ${h_user}@${h_ip} '/usr/local/src/${artifactId}/latest/startup.sh'



# 项目
pipeline_name='jingnan_mall_demo'
jenkins_workspace_home='data/jenkins_data_temp/workspace'
artifactId='jingnan_web'
project_jar_name='${artifactId}-${VERSION}.jar'
project_home='${pipeline_name}/${artifactId}'
h_ip='192.168.200.128'
h_port='22'
h_user='root'
h_pwd='123456'
VERSION='1.0-SNAPSHOT'

# --------------------------------------



