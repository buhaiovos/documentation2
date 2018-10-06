$(document).ready(function() {
        $('#ControlDictionaryTableContainer').jtable({
            title : 'Словник контрольних заходів',
            editinline: { enable : true },
            actions : {
                listAction: 'control-dictionary?action=list',
                createAction: 'control-dictionary?action=create',
                updateAction: 'control-dictionary?action=update',
                deleteAction: 'control-dictionary?action=delete'
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