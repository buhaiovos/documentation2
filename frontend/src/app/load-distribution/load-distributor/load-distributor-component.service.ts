import { Injectable } from '@angular/core';

@Injectable()
export class LoadDistributorComponentService {

  constructor() {
  }

  getTypeName(type: string) {
    switch (type) {
      case 'lectures':
        return 'Лекції';
      case 'practices':
        return 'Практики';
      case 'labs':
        return 'Лаби';
      case 'individuals':
        return 'Інд. заняття';
      case 'exams':
        return 'Екзамени';
      case 'credits':
        return 'Заліки';
      case 'contr_works':
        return 'Контр. роботи';
      case 'course_projs':
        return 'Курс. проекти';
      case 'course_works':
        return 'Курс. роботи';
      case 'rgr':
        return 'РГР';
      case 'dkr':
        return 'ДКР';
      case 'referats':
        return 'Реферати';
      case 'consult':
        return 'Консультації';
      default:
        return type;
    }
  }
}
