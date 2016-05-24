define(function (require, exports, module) {
    var ex = exports;

    ex.cl = require("ctool");

    ex.cj = require("ctooj");


    ex.datatables = function(callback){
        require.async(
            [
                "_/datatables/DataTables-1.10.10/js/jquery.dataTables",
                "_/datatables/Bootstrap-3.3.5/css/bootstrap.min.css",
                "_/datatables/DataTables-1.10.10/css/dataTables.bootstrap.min.css",
                "_/datatables/Select-1.1.0/css/select.bootstrap.min.css"
            ],
            function(){
                require.async(
                    [
                        "_/datatables/DataTables-1.10.10/js/dataTables.bootstrap.min",
                        "_/datatables/Select-1.1.0/js/dataTables.select.min"
                    ],
                    function(){
                        (callback || $.noop)();
                    }
                )
            }
        );
    }
});