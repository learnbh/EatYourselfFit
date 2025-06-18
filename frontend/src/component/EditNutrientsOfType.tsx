import NutrientInput from "./NutrienInput.tsx";
import ShowDailyNutrientData from "./ShowDailyNutrientData.tsx";
import type {Nutrient} from "../types.ts";
import type {ChangeEvent} from "react";

type Props = {
    header:string
    nutrients:Nutrient[]
    dailyNutrients:Nutrient[]
    onChange: (e:ChangeEvent<HTMLInputElement>)=>void
}

export default function EditNutrientsOfType(props:Readonly<Props>){

    function handleChange(e:ChangeEvent<HTMLInputElement>){
        props.onChange(e);
    }

    function setKey(unit:string, name:string):string{
        return unit !== "kJ"
             ? name
             : name + "KJ";
    }

    return(
        <>
            <h1>{ props.header }:</h1>
            <div className="border p-2 mb-2 mt-2">
                { props.nutrients.map(n=>(
                    <NutrientInput
                        key={ setKey(n.unit, n.name) }
                        name={ setKey(n.unit, n.name) }
                        value={ Number(n.quantity) }
                        onChange={ handleChange }
                    >
                    <span>{ " " + n.unit }</span>
                    {
                        props.dailyNutrients
                        .filter(daily=>
                            ((n.unit !== "kcal") && (n.unit !== "kJ") && (n.name === daily.name))
                            ||
                            ((n.unit === daily.unit) && (n.name === daily.name))
                        )
                        .map(daily=> daily.quantity > 0
                            ? <ShowDailyNutrientData
                                key = { "daily_"+setKey(daily.unit, daily.name) }
                                quantity={ daily.quantity }
                                unit={ " " + daily.unit }
                            />
                            : null
                        )
                    }
                    </NutrientInput>
                )) }
            </div>
        </>
    )
}