$(document).ready(function() {
        $('#EducationFormTableContainer').jtable({
            title : 'Форми навчання',
            editinline: { enable : true },
            actions : {
                listAction : 'EducationFormController?action=list',
                createAction : 'EducationFormController?action=create',
                updateAction : 'EducationFormController?action=update',
                deleteAction : 'EducationFormController?action=delete'
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