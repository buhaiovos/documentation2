$(document).ready(function() {
        $('#StateCertificationTableContainer').jtable({
            title: 'Державні атестації',
            editinline: { enable : true },
            actions : {
                listAction: 'state-certification?action=list',
                createAction: 'state-certification?action=create',
                updateAction: 'state-certification?action=update',
                deleteAction: 'state-certification?action=delete'
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