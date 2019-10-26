$(document).ready(function() {
        $('#PracticeTableContainer').jtable({
            title : 'Освітньо-кваліфікаційні рівні',
            editinline: { enable : true },
            actions : {
                listAction: 'practice?action=list',
                createAction: 'practice?action=create',
                updateAction: 'practice?action=update',
                deleteAction: 'practice?action=delete'
            },
            fields : {
                id : {
                    title : 'Ідентифікатор',
                    key : true,
                    list : false,
                    create : false,
                    edit : false        
                },
                type: {
                    title : 'Вид практики',
                    options: {
                        'PRE_DIPLOMA': 'Переддипломна',
                        'PEDAGOGICAL': 'Педагогічна',
                        'SCI_RESEARCH': 'Науково-дослідна'
                    }
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