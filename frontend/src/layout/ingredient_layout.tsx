import type {Ingredient, IngredientCreate} from "../types.ts";
import type {ChangeEvent} from "react";
import InputNumber from "../component/InputNumber.tsx";
import InputText from "../component/InputText.tsx";

type Props = {
    ingredient:Ingredient | IngredientCreate | undefined
    onChange: (e:ChangeEvent<HTMLInputElement>)=>void
}

export default function IngredientLayout(props:Readonly<Props>){

    function handleChange(e:ChangeEvent<HTMLInputElement>){
        props.onChange(e);
    }
    return(
        <>
            {props.ingredient && (
                <div>
                    <div className="flex flex-row gap-2 justify-center items-center border p-2 mb-2 mt-2">
                        <InputText
                        label="Produkt"
                        name="product"
                        value={String(props.ingredient.product)}
                        onChange={handleChange}
                        />
                        <InputText
                            label="Variation"
                            name="variation"
                            value={String(props.ingredient.variation)}
                            onChange={handleChange}
                        />
                        <InputNumber
                            label="Menge"
                            name="quantity"
                            value={Number(props.ingredient.quantity)}
                            onChange={handleChange}
                        >
                            <span>{props.ingredient.unit}</span>
                        </InputNumber>
                        <InputNumber
                            label="Preis"
                            name="prices"
                            value={Number(props.ingredient.prices)}
                            onChange={handleChange}
                        >
                            <span>€</span>
                        </InputNumber>
                    </div>
                    <div className="flex flex-row gap-2 justify-center items-center">
                    </div>
                </div>
            )}
        </>
    )
}