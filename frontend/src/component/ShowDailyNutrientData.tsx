type Props = {
    quantity:number
    unit:string
}
export default function ShowDailyNutrientData(props:Readonly<Props>){
    return(
        <>
          <span>&#8709; { props.quantity }{ props.unit }</span>
        </>
    )
}