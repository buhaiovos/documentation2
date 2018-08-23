$(document).ready(function() {
        $('#SubjectTypeTableContainer').jtable({
            title : 'Типи предметів',
            editinline: { enable : true },
            actions : {
                listAction : 'SubjectTypeController?action=list',
                createAction : 'SubjectTypeController?action=create',
                updateAction : 'SubjectTypeController?action=update',
                deleteAction : 'SubjectTypeController?action=delete'
            },
            fields : {
                id : {
                    title : 'Ідентифікатор',
                    key : true,
                    list : false,
                    create : false,
                    edit : false        
                },
                denotation : {
                    title : 'Назва',
                    edit : true
                }
            }
        });
        $('#SubjectTypeTableContainer').jtable('load');
});