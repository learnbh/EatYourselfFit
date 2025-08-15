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
                        n.unit === "kcal"
                            ? props.dailyNutrients
                                .filter(daily => daily.unit === "kcal")
                                .map(daily =>
                                    <ShowDailyNutrientData
                                        key={"daily_" + setKey(daily.unit, daily.name)}
                                        quantity={daily.quantity}
                                        unit={" " + daily.unit}
                                    />
                                )
                            : n.unit === "kJ"
                                ? props.dailyNutrients
                                    .filter(daily => daily.unit === "kJ")
                                    .map(daily =>
                                        <ShowDailyNutrientData
                                            key={"daily_" + setKey(daily.unit, daily.name)}
                                            quantity={daily.quantity}
                                            unit={" " + daily.unit}
                                        />
                                    )
                                : props.dailyNutrients
                                    .filter(daily => daily.name === n.name)
                                    .map(daily =>
                                        <ShowDailyNutrientData
                                            key={"daily_" + setKey(daily.unit, daily.name)}
                                            quantity={daily.quantity}
                                            unit={" " + daily.unit}
                                        />
                                    )
                    }
                    </NutrientInput>
                )) }
            </div>
        </>
    )
}