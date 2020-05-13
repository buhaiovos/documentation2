import {Component} from '@angular/core';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent {
  public barChartOptions: any = {
    scaleShowVerticalLines: false,
    scales: {
      yAxes: [{
        display: true,
        ticks: {
          suggestedMin: 0,    // minimum will be 0, unless there is a lower value.
          // OR //
          beginAtZero: true   // minimum value will be 0.
        }
      }]
    },
    responsive: true,
    legend: {
      labels: {
        fontSize: 20
      }
    }
  };
  public barChartLabels: string[] = ['Безносик', 'Кисельов', 'Харченко', 'Петренко']; // all teachers
  public barChartType = 'bar';
  public barChartLegend: true;

  public barChartDataAll: any[][] = [
    [{data: [36, 72, 52, 72], label: 'Лекції'}],
    [{data: [0, 0, 7, 0], label: 'Практичні'}],
    [{data: [100, 0, 9, 0], label: 'Лабораторні'}],
    [{data: [0, 9, 18, 4], label: 'Індивідуальні заняття'}],
    [{data: [0, 6, 76, 0], label: 'Екзамени'}],
    [{data: [0, 0, 0, 4], label: 'Заліки'}],
    [{data: [9, 0, 14, 0], label: 'Контрольні роботи'}],
    [{data: [0, 0, 0, 0], label: 'Курсові проекти'}],
    [{data: [13.5, 23.6, 17, 0], label: 'Курсові роботи'}],
    [{data: [0, 0, 0, 0], label: 'РГР'}],
    [{data: [0, 0, 0, 0], label: 'ДКР'}],
    [{data: [0, 0, 0, 0], label: 'Реферати'}],
    [{data: [15, 14.4, 13.5, 13.54], label: 'Консультації'}],
  ];

  public barChartOtherDataAll: any[][] = [
    [{data: [0, 20, 15, 5, 50], label: 'Петренко'}],
    [{data: [0, 12, 10, 5, 0], label: 'Кисельов'}]
  ];

  public otherBarChartLabels: string[] = [
    'Індивід. заняття з дисципліни наукових досліджень',
    'Керівництво практикою: переддиплом.: ДА-51с, ДА-52с',
    'Керівництво атестаційною роботою: магістри ОПП ДА-51с, ДА-52с',
    'Робота в ЕК: захист дипломів: магістри ОПП ДА-51с, ДА-52с',
    'Керівництво: аспірантами Кислий, Яременко'
  ];

  public otherLoadBarType = 'horizontalBar';

  public barChartOverallDataAll: any[][] = [
    [{data: [152, 202.3, 189, 242.2], label: 'Всього'}]
  ];

  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }

}
