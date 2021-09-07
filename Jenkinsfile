pipeline {
    agent any

    options {
      //1.1 超时构建退出【SECONDS、MINUTES、HOURS】
      timeout(time: 10, unit: 'MINUTES')
      //1.2 显示构建次数
       buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    tools {
      //配置Maven环境
      maven 'maven3.6.1'
    }
    environment {
      //代码仓库路径
      repo_code_dir='/usr/local/src/${artifactId}/${version}/'
      //目标代码仓库路径
      target_server_dir='/usr/local/src/${artifactId}/latest/'
    }
    stages {
      //1.拉取代码
        stage('git拉取代码'){
            when {
                expression {
                  return  ("${DEPLOY}" == "upgrade")
                }
            }
            steps{
              ////1.2 参数化构建，URL地址和分支进行参数化构建
              git credentialsId: 'gitlab_root_password',url: '${URL}',branch:'${BRANCH}'
            }
        }
        //2.代码测试
//        stage('Junit') {
//            when {
//                expression {
//                  return  ("${DEPLOY}" == "upgrade")
//                }
//            }
//            steps {
//               //-Dmaven.test.failu-re.ignore=true 如果测试过程中报错了，不中断pipeline
//                sh "mvn clean test -pl ${artifactId} -am -Dmaven.test.failure.ignore=true"
//                junit '**/target/surefire-reports/*.xml'
//            }
//        }

        //3.构建
        stage('build'){
            when {
                expression {
                  return  ("${DEPLOY}" == "upgrade")
                }
            }
            steps{
              sh 'mvn clean package -Dmaven.test.skip=true -pl ${artifactId} -am'
            }
        }
        //4. 移动到代码仓库
        stage('移动到代码仓库'){
            when {
                expression {
                  return  ("${DEPLOY}" == "upgrade")
                }
            }
            steps{
              sh "mkdir -p ${repo_code_dir}"
              sh "cp ${WORKSPACE}/${artifactId}/target/${artifactId}-${version}.jar ${repo_code_dir}"
              sh "cp ${WORKSPACE}/${artifactId}/target/classes/startup.sh ${repo_code_dir}"
              sh "cp ${WORKSPACE}/${artifactId}/target/classes/stop.sh ${repo_code_dir}"
            }
        }
        //5. 发布代码到目标服务器【one】
        stage('发布服务|回滚'){
            steps{
              script {
                for(ip in ts_ips.tokenize(',')){
                  sh "ssh -p ${ts_port} ${ts_user}@${ip} 'rm -rf ${target_server_dir}*'"
                  sh "scp -P ${ts_port} ${repo_code_dir}${artifactId}-${version}.jar ${ts_user}@${ip}:${target_server_dir}"
                  sh "scp -P ${ts_port} ${repo_code_dir}startup.sh ${ts_user}@${ip}:${target_server_dir}"
                  sh "scp -P ${ts_port} ${repo_code_dir}stop.sh ${ts_user}@${ip}:${target_server_dir}"
                }

              }
            }
        }
        //6. 远程启动目标服务
        stage('启动服务'){
            steps{
              script {
                for(ip in ts_ips.tokenize(',')){
                    sh "ssh -p ${ts_port} ${ts_user}@${ip} 'chmod 755 ${target_server_dir}stop.sh'"
                    sh "ssh -p ${ts_port} ${ts_user}@${ip} '${target_server_dir}stop.sh'"
                    sh "ssh -p ${ts_port} ${ts_user}@${ip} 'chmod 755 ${target_server_dir}startup.sh'"
                    sh "ssh -p ${ts_port} ${ts_user}@${ip} '${target_server_dir}startup.sh'"
                }
              }
            }
        }
    }
}
