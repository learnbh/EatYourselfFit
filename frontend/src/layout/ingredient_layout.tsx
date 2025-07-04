import type {Ingredient, IngredientCreate, IngredientProductRef, InputRef} from "../types.ts";
import React, {type ChangeEvent, forwardRef, useImperativeHandle, useRef} from "react";
import InputText from "../component/InputText.tsx";
import InputNumber from "../component/InputNumber.tsx";

type Props = {
    ingredient:Ingredient | IngredientCreate | undefined
    onChange: (e:ChangeEvent<HTMLInputElement>)=>void
}

function IngredientLayout( props:Readonly<Props>, ref: React.Ref<IngredientProductRef> ){
    const refProduct = useRef<InputRef>(null);
    const refVariation = useRef<InputRef>(null);

    useImperativeHandle(ref, () => ({
        focusField: (fieldName: string) => {
            if (fieldName === 'product') {
                refProduct.current?.focus();
            } else
            if (fieldName === 'variation') {
                refProduct.current?.focus();
            }
        },
    }));

    function handleChange( e:ChangeEvent<HTMLInputElement> ){
        props.onChange( e );
    }

    return(
        <>
            {props.ingredient && (
                <div>
                    <div className="flex flex-row gap-2 justify-center items-center border p-2 mb-2 mt-2">
                        <InputText
                            label="Produkt"
                            name="product"
                            ref = { refProduct }
                            value={String(props.ingredient.product)}
                            onChange={handleChange}
                        />
                        <InputText
                            label="Variation"
                            name="variation"
                            ref = { refVariation }
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
export default forwardRef(IngredientLayout);