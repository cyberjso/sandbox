#Fonte: http://www.michael-noll.com/tutorials/running-hadoop-on-ubuntu-linux-single-node-cluster/

cd /opt/
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u60-b27/jdk-8u60-linux-x64.tar.gz"
sudo tar xzf jdk-8u60-linux-x64.tar.gz

cd /opt/jdk1.8.0_60/

sudo alternatives --install /usr/bin/java java /opt/jdk1.8.0_60/bin/java 2

echo 'export JAVA_HOME=/opt/jdk1.8.0_60' >> ~/.bashrc
source ~/.bashrc


sudo groupadd hadoop

sudo useradd hduser -g hadoop

sudo passwd hduser



su - hduser

ssh-keygen -t rsa -P ""

sudo cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys

exit

sudo visudo

# adicionar hduser  ALL=(ALL)       ALL

su - hduser

sudo vi /etc/sysctl.conf


#install hadoop
wget http://ftp.unicamp.br/pub/apache/hadoop/core/hadoop-2.7.1/hadoop-2.7.1.tar.gz
tar xvf hadoop-2.7.1.tar.gz
sudo mv hadoop-2.7.1 /usr/local
rm -rf hadoop-2.7.1.tar.gz
sudo chown -R hduser:hadoop /usr/local/hadoop-2.7.1

#make datanode see the masterNode
echo '192.168.34.10     nameNode' | sudo tee --append  /etc/hosts

##############################################################################
# set ~/.bashrc  like this

# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
        . /etc/bashrc
fi

# Uncomment the following line if you don't like systemctl's auto-paging feature:
# export SYSTEMD_PAGER=

# User specific aliases and functions
export JAVA_HOME=/opt/jdk1.8.0_60

# Set Hadoop-related environment variables
export HADOOP_HOME=/usr/local/hadoop-2.7.1


# Some convenient aliases and functions for running Hadoop-related commands
unalias fs &> /dev/null
alias fs="hadoop fs"
unalias hls &> /dev/null
alias hls="fs -ls"

# If you have LZO compression enabled in your Hadoop cluster and
# compress job outputs with LZOP (not covered in this tutorial):
# Conveniently inspect an LZOP compressed file from the command
# line; run via:
#
# $ lzohead /hdfs/path/to/lzop/compressed/file.lzo
#
# Requires installed 'lzop' command.
#
lzohead () {
    hadoop fs -cat $1 | lzop -dc | head -1000 | less
}

# Add Hadoop bin/ directory to PATH
export PATH=$PATH:$HADOOP_HOME/bin
##############################################################################



# on master node
ssh-copy-id -i $HOME/.ssh/id_rsa.pub hduser@192.168.34.12
echo 'dataNode1' >> $HADOOP_HOME/etc/hadoop/slaves




 
 