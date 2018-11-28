$(document).ready(function () {
    $('#StaffTableContainer').jtable({
        title: 'Люди',
        editinline: {enable: true},
        actions: {
            listAction: 'staff?action=list',
            createAction: 'staff?action=create',
            updateAction: 'staff?action=update',
            deleteAction: 'staff?action=delete'
        },
        fields: {
            id: {
                title: 'Ідентифікатор',
                key: true,
                list: false,
                create: false,
                edit: false
            },
            firstName: {
                title: 'Ім\'я',
                edit: true
            },
            middleName: {
                title: 'По-батькові',
                edit: true
            },
            lastName: {
                title: 'Прізвище',
                edit: true
            },
            type: {
                title: 'Вид діяльності',
                options: {
                    'POSTGRADUATE': 'Аспірант',
                    'TRAINEE': 'Здобувач/стажер',
                    'DOCTORAL_STUDENT': 'Докторант',
                    'PROFESSOR': 'Викладач'
                }
            },
            educationForm: {
                title: 'Форма навчання',
                options: 'form-of-education?action=dropdownlist',
                edit: true
            },
            sourceOfFinancing: {
                title: 'Фінансування',
                options: {
                    'Budgetary': 'Бюджет',
                    'Contract': 'Контракт'
                }
            },
            rate: {
                title: 'Ставка',
                edit: true
            },
            position: {
                title: 'Посада',
                edit: true
            },
            degree: {
                title: 'Ступінь',
                edit: true
            }
        }
    });
    $('#StaffTableContainer').jtable('load');
});