var exec = require('cordova/exec');

exports.execute = function (arg0, success, error) {
    exec(success, error, 'SecureFlag', 'secureFlag', [arg0]);
};
