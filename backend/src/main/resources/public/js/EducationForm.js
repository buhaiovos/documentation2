$(document).ready(function() {
        $('#EducationFormTableContainer').jtable({
            title : 'Форми навчання',
            editinline: { enable : true },
            actions : {
                listAction: 'form-of-education?action=list',
                createAction: 'form-of-education?action=create',
                updateAction: 'form-of-education?action=update',
                deleteAction: 'form-of-education?action=delete'
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
        $('#EducationFormTableContainer').jtable('load');
});