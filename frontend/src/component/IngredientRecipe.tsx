type Props = {
    product:string,
    variation:string,
    quantity:number,
    unit:string
}

export default function IngredientRecipe(props:Readonly<Props>){
    return(
      <>
          <div className="flex flex-row gap-2">
              <span>{props.product}</span>
              <span>{props.variation}</span>
              <span>{props.quantity}</span>
              <span>{props.unit}</span>
          </div>
      </>
    );
}