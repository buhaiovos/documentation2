$(document).ready(function() {
        $('#ControlDictionaryTableContainer').jtable({
            title : 'Словник контрольних заходів',
            editinline: { enable : true },
            actions : {
                listAction : 'ControlDictionaryController?action=list',
                createAction : 'ControlDictionaryController?action=create',
                updateAction : 'ControlDictionaryController?action=update',
                deleteAction : 'ControlDictionaryController?action=delete'
            },
            fields : {
                id : {
                    title : 'Ідентифікатор',
                    key : true,
                    //list : false,
                    create : false,
                    edit : false        
                },
                denotation : {
                    title : 'Назва',
                    edit : true
                }
            }
        });
        $('#ControlDictionaryTableContainer').jtable('load');
});