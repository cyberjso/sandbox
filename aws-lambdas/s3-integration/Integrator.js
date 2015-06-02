var parser = require('xml2js').parseString;
var AWS = require('aws-sdk');
var async = require('async');

var s3 = new AWS.S3();
var sqs = new AWS.SQS();

exports.handler =  function(event, context) {
	var bucketName = event.Records[0].s3.bucket.name;
	var fileName = event.Records[0].s3.object.key;

	async.waterfall([
		function download(next) {
			s3.getObject({Bucket: bucketName,  Key: fileName}, function (err, data) {
				next(err, data);
			})
		},
		function parseXml(response, next) {
			parser(response.Body.toString(), function(err, result) {
				next(err, result);
			})
		},
		function sendMessage(result, next) {
			var message = {
				MessageBody: JSON.stringify(result),
				QueueUrl: "[YOUR QUEUE URL. i.e: https://......]"
			};
			
			sqs.sendMessage(message, function(err, data) {
			   if(err) {
			      context.fail("Error: " + err);
			    } else {
			      context.succeed("Message sent succefully: " + data.MessageId); 
			    }
			    context.done();
			});
		}
	], function(err) {
		if (err) {
			context.fail("Error: " + err);
			throw err;
		}
	});

}