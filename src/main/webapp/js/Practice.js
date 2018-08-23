$(document).ready(function() {
        $('#PracticeTableContainer').jtable({
            title : 'Освітньо-кваліфікаційні рівні',
            editinline: { enable : true },
            actions : {
                listAction : 'PracticeController?action=list',
                createAction : 'PracticeController?action=create',
                updateAction : 'PracticeController?action=update',
                deleteAction : 'PracticeController?action=delete'
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
                    title : 'Вид практики',
                    edit : true
                },
                semester : {
                    title : 'Семестр',
                    edit : true
                },
                weeks : {
                    title : 'Тривалість у тижнях',
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
        $('#PracticeTableContainer').jtable('load');
});