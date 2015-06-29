cd src

rm -rf *.zip

npm install

zip  integrator.zip *.js *.json -r node_modules

cd ..

kappa config.yml create

kappa config.yml add_event_source

kappa config.yml invoke

kappa config.yml status

