package extras.render

import eu.timepit.refined.api.RefType

/** @author Kevin Lee
  * @since 2022-11-22
  */
package object refined {

  /** `Render` instance for refined types that delegates to the `Render`
    * instance of the base type.
    */
  implicit def refTypeRender[F[_, _], T: Render, P](implicit rt: RefType[F]): Render[F[T, P]] =
    Render.render((Render[T].render _).compose(rt.unwrap))

}
