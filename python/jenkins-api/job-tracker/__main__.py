from jenkinsapi.jenkins import Jenkins
import sys



def get_server_instance():
    jenkins_host = str(sys.argv[2])
    jenkins_user = str(sys.argv[3])
    jenkins_password = str(sys.argv[4])
    
    return Jenkins('http://localhost:8080/', username = jenkins_user, password = jenkins_password)

def get_running_jobs():
    server  = get_server_instance() 
    running_jobs = []
    for j in server.get_jobs():
        job_instance = server.get_job(j[0])
        if job_instance.is_running():
            running_jobs.append(job_instance.name)

    return running_jobs        

def shouldRun(job_name):
    running_jobs = get_running_jobs();

    if (job_name == 'decorator' and len(running_jobs) > 0):
        return False

    jobs = filter(lambda x: x  == 'decorator', running_jobs)
    if (len(jobs) > 0):
        return True

    return True

if __name__ == '__main__':
    condition = 0
    job_name = str(sys.argv[1])
    
    while (condition == 0):
        if (shouldRun(job_name) is False):
            print job_name  + ' should run alone and there are other jobs currently running. Wait a bit more'
        else:
            print job_name + ' ok to go!'
            break