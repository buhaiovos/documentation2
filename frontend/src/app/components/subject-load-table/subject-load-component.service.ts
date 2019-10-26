import {Injectable} from '@angular/core';
import {Load} from '../../models/load.model';

@Injectable()
export class SubjectLoadComponentService {

  constructor() {
  }

  addOrRemoveElementFromSelected(selectedType: string, selectedElement: Load, selectedElements: Map<Load, string[]>) {
    const selectedTypes = selectedElements.get(selectedElement);
    if (selectedTypes && selectedTypes.find(type => type === selectedType)) {
      const index = selectedTypes.indexOf(selectedType, 0);
      selectedTypes.splice(index, 1);
      if (selectedTypes.length === 0) {
        selectedElements.delete(selectedElement);
      }
    } else {
      if (selectedTypes) {
        selectedTypes.push(selectedType);
      } else {
        selectedElements.set(selectedElement, [selectedType]);
      }
    }
  }

  isSelected(type: string, element: Load, selectedElements: Map<Load, string[]>) {
    const selectedTypesForElement = selectedElements.get(element);
    if (selectedTypesForElement) {
      return selectedTypesForElement.find(selectedType => selectedType === type);
    } else {
      return false;
    }
  }

  hasError(load: Load) {
    const hours = Object.keys(load.elements).map(key => load.elements[key]);
    for (const hour of hours) {
      if (hour < 0) {
        return true;
      }
    }

    return false;
  }

  isDone(load: Load) {
    const hours = Object.keys(load.elements).map(key => load.elements[key]);
    for (const hour of hours) {
      if (hour > 0) {
        return false;
      }
    }

    return true;
  }

  showDistributor(selectedElements: Map<Load, string[]>) {
    return selectedElements.size > 0;
  }
}

