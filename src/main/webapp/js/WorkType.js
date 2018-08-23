$(document).ready(function() {
        $('#WorkTypeTableContainer').jtable({
            title : 'Види робіт з підготовки дипломного проекту',
            editinline: { enable : true },
            actions : {
                listAction : 'WorkTypeController?action=list',
                createAction : 'WorkTypeController?action=create',
                updateAction : 'WorkTypeController?action=update',
                deleteAction : 'WorkTypeController?action=delete'
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
        $('#WorkTypeTableContainer').jtable('load');
});