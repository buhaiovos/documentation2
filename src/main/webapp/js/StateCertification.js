$(document).ready(function() {
        $('#StateCertificationTableContainer').jtable({
            title : 'Освітньо-кваліфікаційні рівні',
            editinline: { enable : true },
            actions : {
                listAction : 'StateCertificationController?action=list',
                createAction : 'StateCertificationController?action=create',
                updateAction : 'StateCertificationController?action=update',
                deleteAction : 'StateCertificationController?action=delete'
            },
            fields : {
                id : {
                    title : 'Ідентифікатор',
                    key : true,
                    list : false,
                    create : false,
                    edit : false        
                },
                form : {
                    title : 'Форма державної атестації',
                    edit : true
                },
                semester : {
                    title : 'Семестр',
                    edit : true
                },
                start : {
                    title: 'Дата початку',
                    type: 'date',
                    displayFormat: 'dd.mm.yy'
                },
                finish : {
                    title: 'Дата завершення',
                    type: 'date',
                    displayFormat: 'dd.mm.yy'
                }
            }
        });
        $('#StateCertificationTableContainer').jtable('load');
});