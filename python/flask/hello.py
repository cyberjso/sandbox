from flask import Flask

app = Flask(__name__)

@app.route('/my-json-document')
def hello2():
    """ Load a json document from a text file and return it to the user """

     with open('hello.json', 'r') as f:
         return f.read()
