$(document).ready(function() {
        $('#QualificationTableContainer').jtable({
            title : 'Освітньо-кваліфікаційні рівні',
            editinline: { enable : true },
            actions : {
                listAction : 'QualificationController?action=list',
                createAction : 'QualificationController?action=create',
                updateAction : 'QualificationController?action=update',
                deleteAction : 'QualificationController?action=delete'
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
        $('#QualificationTableContainer').jtable('load');
});