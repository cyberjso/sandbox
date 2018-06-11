#!/bin/sh

#  References:
# - https://cloud.google.com/compute/docs/instances/adding-removing-ssh-keys

project_name=jack-test-project

# Crete VPC
gcloud compute networks create main-vpc --subnet-mode=custom

#Create subnets inside the VPC main-vpc
gcloud compute networks subnets create main-vpc-us-west1-a --network=main-vpc --range=172.16.0.0/16 --region=us-west1
gcloud compute networks subnets create main-vpc-us-west1-b --network=main-vpc --range=172.17.0.0/16 --region=us-west1
gcloud compute networks subnets create main-vpc-us-west1-c --network=main-vpc --range=172.18.0.0/16 --region=us-west1

# Creating permissions
gcloud iam service-accounts create  bastion --display-name "Bastion"
gcloud projects add-iam-policy-binding  $project_name    \
 --member serviceAccount:bastion@$project_name.iam.gserviceaccount.com --role roles/dns.admin

# Allow inbound rules for bastion hosts
gcloud compute firewall-rules create bastion-ssh --network main-vpc --allow tcp:22 --target-tags bastion

bastion_name=bastion

gcloud compute instances create $bastion_name \
--machine-type=g1-small \
--image-project=ubuntu-os-cloud \
--image-family=ubuntu-1604-lts \
--boot-disk-size=10GB \
--network=main-vpc \
--subnet=main-vpc-us-west1-a \
--zone=us-west1-b \
--subnet=main-vpc-us-west1-b \
--labels role=bastion \
--tags bastion \
--service-account=bastion@$project_name.iam.gserviceaccount.com \
--scopes "https://www.googleapis.com/auth/cloud-platform" \
--preemptible \
--restart-on-failure \
--metadata  enable-oslogin=TRUE

gcloud compute instances add-metadata $bastion_name \
--metadata startup-script='#! /bin/bash
gcloud dns managed-zones create my-zone --description="Zone created for testing purposes" --dns-name myzone.io.com.

my_external_ip=$(curl -H "Metadata-Flavor: Google" http://metadata/computeMetadata/v1/instance/network-interfaces/0/access-configs/0/external-ip)

gcloud dns record-sets transaction start --zone my-zone
gcloud dns --project=$project_name record-sets transaction add  $my_external_ip \
--name=bastion.myzone.io.com. \
--ttl=60 \
--type=A \
--zone=my-zone
gcloud dns record-sets transaction execute --zone my-zone
'

# Set public key for the current user logged via gcloud.
gcloud compute os-login ssh-keys add --key-file ~/.ssh/id_rsa.pub

# Run this command in order to know which user to use on the SSH login
#gcloud compute os-login describe-profile
