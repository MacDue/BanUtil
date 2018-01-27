"use strict";
function UniJClient(serverAddress) {

    var self = this;
    // Initialized by UniJ server
    var clientName = "no-name-yet";
    var websocket;
    var localProcedures = {};

    this.addProcedure = function(name, procedure) {
        if (localProcedures[name] !== undefined) {
            log("Local procedure with name \"" + name + "\" has been overwritten");
        }
        localProcedures[name] = procedure;
    };

    this.removeProcedure = function(name) {
        if (localProcedures[name] === undefined) {
            log("Removing local procedure with name \"" + name + "\" is unnecessary because it doesn't exit");
        } else {
            delete localProcedures[name];
        }
    };

    /**
     * Sends a proposal to change this clients name. Server then changes it or not
     * @param newName
     */

    this.setClientName = function (newName) {
        if (websocket === undefined) {
            log("You can only set the client's name if you are connected to the server");
        } else {
            this.execute("setClientName", clientName, newName);
        }
    };

    /**
     * Get the name of this UniJ client
     * @returns {string}
     */

    this.getClientName = function () {
        return clientName;
    };


    /**
     * Initialize UniJ client and add a callback function
     * @param callback
     */

    this.onReady = function(callback) {

        // Server can log to client directly
        this.addProcedure("unijLog", function (msg) {
            console.log(msg);
        });
        // Server can set clients name remotely
        this.addProcedure("setClientName", function (name) {
            clientName = name;
        });

        // Tell client that it is registered at the server
        this.addProcedure("clientIsReadyNow", function () {
            log("Connected to server!");
            callback();
        });

        websocket = new WebSocket(buildWebSocketAddress(serverAddress));

        websocket.onmessage = function(event) {

            var message = JSON.parse(event.data);
            var procedure = localProcedures[message.pro];

            if (procedure !== undefined) {
                procedure.apply(this, message.par);
            } else {
                logToServer("I don't know procedure with name \"" + message.pro + "\"");
            }
        };

        websocket.onclose = function(event) {
            var reason = (event.reason === "") ? "unexpected closing" : event.reason;
            log("Lost connection from server because of " + reason);
        };
    };

    this.execute = function (remoteProcedureName, parameters) {
        if (websocket !== undefined) {
            // Minified JSON to reduce parsing steps
            websocket.send("{\"" + remoteProcedureName + "\":" +
                JSON.stringify(Array.prototype.slice.call(arguments, 1)) + "}");
        } else {
            log("You cannot execute remote procedures without connecting to the UniJ server first");
        }
    };

    function buildWebSocketAddress(serverAddress) {
        // For browsers
        if (serverAddress === undefined) {
            var protocol = (location.protocol === "https:") ? "wss://" : "ws://";
            return protocol + location.host + "/unij";

        // only in Node.js
        } else {

            var validWS = /^ws[s]*:\/\/[\w\.]+[:0-9]*$/;

            if (validWS.test(serverAddress)) {
                return serverAddress + "/unij";
            } else {
                throw "An invalid WebSocket address was passed to UniJ: \"" + serverAddress + "\". Try something like \"ws://localhost:7777\"";
            }
        }
    }

    /******************************* Logging *******************************/

    /**
     * Send logs to the UniJ server
     * @param message   Contains the log
     */

    function logToServer(message) {
        self.execute("unijLog", formatLog(message));
    }

    /**
     * Construct a client log message
     * @param message       Contains the log
     * @returns {string}
     */

    function formatLog(message) {
        return "UniJ Client \"" + clientName + "\": " + message;
    }

    /**
     * Regular console.log() but formatted
     * @param message
     */

    function log(message) {
        console.log(formatLog(message));
    }
}

var UniJ = new UniJClient();