type Props = {
    quantity:number
    unit:string
}
export default function ShowDailyNutrientData(props:Readonly<Props>){
    return(
        <>
            <span>&#8709; Tagesbedarf: </span>
          <span>{ props.quantity }{ props.unit }</span>
        </>
    )
}