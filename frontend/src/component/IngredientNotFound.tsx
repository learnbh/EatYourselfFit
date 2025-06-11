import React from "react";

type Props = {
    addPerAi:() =>  void
    addPerUser:() =>  void
    abort:() =>  void
}
export default function IngredientNotFound(props:Readonly<Props>) {

    function addPerAi(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        props.addPerAi();
    }
    function addPerUser(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        props.addPerUser();
    }
    function abort(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        props.abort();
    }
    return (
        <>
            <div className="flex flex-col justify-items-start gap-4 w-full pt-2 border">
                <span>Zutat ist noch nicht in der Datenbank vorhanden!
                    <br/>
                    Möchtest Du die Zutat selber oder über KI hinzufügen?
                </span>
                <div className="grid grid-cols-4 items-center gap-4 w-full border p-2">
                    <button
                        className=" border"
                        onClick={addPerAi}
                    >
                        KI
                    </button>
                    <button
                        className=" border"
                        onClick={addPerUser}
                    >
                        Ich
                    </button>
                    <button
                        className="col-span-2 border"
                        onClick={abort}
                    >
                        Abbrechen
                    </button>
                </div>
            </div>
        </>
    )
}