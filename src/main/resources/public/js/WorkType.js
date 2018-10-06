$(document).ready(function() {
        $('#WorkTypeTableContainer').jtable({
            title : 'Види робіт з підготовки дипломного проекту',
            editinline: { enable : true },
            actions : {
                listAction: 'work-type?action=list',
                createAction: 'work-type?action=create',
                updateAction: 'work-type?action=update',
                deleteAction: 'work-type?action=delete'
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