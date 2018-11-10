$(document).ready(function() {
        $('#SubjectTypeTableContainer').jtable({
            title : 'Типи предметів',
            editinline: { enable : true },
            actions : {
                listAction: 'subject-types?action=list',
                createAction: 'subject-types?action=create',
                updateAction: 'subject-types?action=update',
                deleteAction: 'subject-types?action=delete'
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