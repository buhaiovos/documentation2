import { Observable, of } from "rxjs";
import { take } from "rxjs/operators";

export class Utils {
  public static noopFunction: () => void = () => {
  };

  public static takeOne$: <T>(obs: Observable<T>) => Observable<T> = obs => obs.pipe(take(1));

  public static fetchOrCreate$:
    <T>(fetchFunction: (fetchId: number) => Observable<T>, createFunction: () => T, fetchId?: any) => Observable<T> =
    (fetchFunction, createFunction, fetchId?) => {
      return fetchId
        ? fetchFunction(+fetchId)
        : of(createFunction());
    }
}
