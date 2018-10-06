$(document).ready(function() {
        $('#QualificationTableContainer').jtable({
            title : 'Освітньо-кваліфікаційні рівні',
            editinline: { enable : true },
            actions : {
                listAction: 'qualification?action=list',
                createAction: 'qualification?action=create',
                updateAction: 'qualification?action=update',
                deleteAction: 'qualification?action=delete'
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