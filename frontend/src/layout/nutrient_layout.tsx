import type {Nutrient, Nutrients} from "../types.ts";
import {type ChangeEvent, useCallback, useEffect, useState} from "react";
import axios from "axios";
import EditNutrientsOfType from "../component/EditNutrientsOfType.tsx";
import {mapNutrientsToNutrientArray} from "../helper.ts";

type Props = {
    nutrients:Nutrient[]|undefined
    onChange: (e:ChangeEvent<HTMLInputElement>)=>void
}

export default function NutrientLayout(props:Readonly<Props>) {

    const [dailyNutrients, setDailyNutrients] = useState<Nutrient[]>([])
    const [isLoading, setLoading] = useState<boolean>(true)

    function handleChange(e: ChangeEvent<HTMLInputElement>) {
        props.onChange(e);
    }

    const getDetails = useCallback(async () => {
        try{
            setLoading(true)
            const responseNutrient = await axios.get<Nutrients[]>("/eyf/ingredients/daily/nutrients")
            setDailyNutrients(mapNutrientsToNutrientArray(responseNutrient));
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        } finally {
            setLoading(false)
        }
    }, []);

    useEffect(() => {
        (async () => {
            await getDetails();
        })();
    }, [getDetails]);

    return (
        <>
            { isLoading && (
                <h1> Einen Moment bitte Zutat wird geladen ...</h1>
            ) }
            { props.nutrients && (
                <div>
                    <EditNutrientsOfType
                        header="Makronährstoffe"
                        nutrients={ props.nutrients.filter(n => n.type === "MACRONUTRIENT") }
                        dailyNutrients={ dailyNutrients.filter(n => n.type === "MACRONUTRIENT") }
                        onChange={ handleChange }
                    />
                    <EditNutrientsOfType
                        header="Vitamine"
                        nutrients={ props.nutrients.filter(n => n.type === "VITAMIN") }
                        dailyNutrients={ dailyNutrients.filter(n => n.type === "VITAMIN") }
                        onChange={ handleChange }
                    />
                    <EditNutrientsOfType
                        header="Mengenelemente"
                        nutrients={ props.nutrients.filter(n => n.type === "MAJORELEMENT") }
                        dailyNutrients={ dailyNutrients.filter(n => n.type === "MAJORELEMENT") }
                        onChange={ handleChange }
                    />
                    <EditNutrientsOfType
                        header="Spurenlemente"
                        nutrients={ props.nutrients.filter(n => n.type === "TRACEELEMENT") }
                        dailyNutrients={ dailyNutrients.filter(n => n.type === "TRACEELEMENT") }
                        onChange={ handleChange }
                    />
                    <EditNutrientsOfType
                        header="Aminosäure essentiell"
                        nutrients={ props.nutrients.filter(n => n.type === "ESSENTIALAMINOACID") }
                        dailyNutrients={ dailyNutrients.filter(n => n.type === "ESSENTIALAMINOACID") }
                        onChange={ handleChange }
                    />
                    <EditNutrientsOfType
                        header="Aminosäure"
                        nutrients={ props.nutrients.filter(n => n.type === "AMINOACID") }
                        dailyNutrients={ dailyNutrients.filter(n => n.type === "AMINOACID") }
                        onChange={ handleChange }
                    />
                </div>
            ) }
        </>
    );
}