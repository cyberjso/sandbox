from flask import Flask, request

app = Flask(__name__)

@app.route('/', defaults={'path': ''}, methods=['GET', 'POST', 'PUT'])
@app.route('/<path:path>',methods=['GET', 'POST', 'PUT'])
def catch_all(path):
    """ Intercepts any request made to the server """
    app.logger.info("request catched! Path reuqested was: {path} ".format(path = path))

    if request.method == 'POST' or request.method == 'PUT':
        app.logger.info(request)

    return "ok"
